#!/usr/bin/python3
# -*- coding:utf-8 -*-
import datetime
import threading
from queue import Queue
import pymysql
import snownlp
import urllib.parse

db = pymysql.connect("localhost", "root", "root", "comment_analysis", charset="utf8")
cursor = db.cursor()
update_cursor = db.cursor()
db.ping(True)
start_position = 1579500
handled_num = 1579500
row_distance = 500
total_rows = 1579882


# 从数据库中读数据
class Producer(threading.Thread):
    def __init__(self, block_queue):
        threading.Thread.__init__(self)
        self.queue = block_queue
        self.exists = False
        pass

    def run(self):
        global start_position
        total_num = self.get_total_rows()
        while True:
            sql = "select * from relation where id >= %d limit 500" % start_position
            try:
                cursor.execute(sql)
                pass
            except Exception as e:
                print("select exception ", str(e))
                db.connect()
                continue
                pass
            records = cursor.fetchall()

            # 队列已满，等待
            while True:
                if Queue.qsize(self.queue) < row_distance:
                    break
                    pass
                pass
            for item in records:
                self.queue.put(item)
                pass
            start_position += row_distance
            if start_position > total_num:
                print("producer thread end")
                break
            pass
        pass

    # 获取数据总数
    @staticmethod
    def get_total_rows():
        sql = "select count(*) from relation"
        cursor.execute(sql)
        record = cursor.fetchone()
        return record[0]


# 向数据库中存数据
class Consumer(threading.Thread):
    def __init__(self, block_queue):
        threading.Thread.__init__(self)
        self.queue = block_queue
        self.queue_handled = False
        self.exists = False
        pass

    def run(self):
        while True:
            self.queue_handled = False
            sql = "replace into relation(`id`, `cid`, `user_name`, `comment`, `evaluation`, `add_time`) values (%s,%s,%s,%s,%s,%s)"
            list = []
            # 队列不为空
            while not Queue.empty(self.queue):
                list.append(self.queue.get())
                global handled_num

                if len(list) == row_distance:
                    result = self.calculate_by_thread(list)
                    self.update_db(sql, result)
                    list.clear()
                    self.queue_handled = True
                    break
                elif handled_num == 1579882:
                    result = self.calculate_by_thread(list)
                    self.update_db(sql, result)
                    list.clear()
                    self.exists = True
                    break
                handled_num += 1
                pass
            if self.queue_handled:
                self.queue.task_done()
                pass
            if self.exists:
                print("consumer thread end")
                break
                pass
            pass
        pass

    @staticmethod
    def update_db(sql, sequence):
        while True:
            try:
                global handled_num
                update_cursor.executemany(sql, sequence)
                # update_cursor.execute(sql)
                db.commit()
                handled_num += 1
                print("have analyzed rows :", handled_num)
                break
            except Exception as e:
                print("update db exception ", str(e))
                db.connect()
                pass
            pass
        pass

    @staticmethod
    def calculate_by_thread(record_list):
        result_list = []
        index = 0
        while True:
            if index >= len(record_list) - 1:
                break
            temp_list = []
            for t in range(0, 2):
                res = CalculateMention(record_list[index + t])
                temp_list.append(res)
                res.start()
                pass

            for t in temp_list:
                t.join()
                result_list.append(t.get_result())
            index += 2
            pass
        return result_list


# 计算文本情感值
class CalculateMention(threading.Thread):
    def __init__(self, item):
        threading.Thread.__init__(self)
        self.record_item = item
        pass

    def run(self):
        self.res_list = []
        for i in range(len(self.record_item) - 1):
            if i == 4:
                text = urllib.parse.unquote(self.record_item[3])
                mention = 0
                if len(text) > 0:
                    mention = self.analysis_mention(text)
                    pass
                self.res_list.append(mention)
                pass
            else:
                self.res_list.append(self.record_item[i])
            pass
        pass

    def get_result(self):
        try:
            return self.res_list
        except Exception as e:
            print("get result exception", str(e))
            pass

    # 进行情感计算
    @staticmethod
    def analysis_mention(text):
        s = snownlp.SnowNLP(text)
        mention = s.sentiments
        if mention > 0.6:
            return 1
        elif mention < 0.4:
            return -1
        else:
            return 0
        pass


# 更新每一个电影对应的情感概率
class CalculationEvaluation():
    def __init__(self):
        pass

    @staticmethod
    def calculation():
        sql = "select cid from video group by cid"
        try:
            cursor.execute(sql)
            pass
        except Exception as e:
            print("select exception ", str(e))
            pass

        cid_res = cursor.fetchall()
        num = 0
        for item in cid_res:
            print(num)
            sql = "select * from relation where cid='%s'" % (item[0])
            try:
                cursor.execute(sql)
                pass
            except Exception as e:
                print(str(e))
                pass
            relation_res = cursor.fetchall()
            # 进行计算
            res_list = CalculationEvaluation.doing_calculation(relation_res)
            update_sql = "update video set good_percent='%s',bad_percent='%s', man_good_percent='%s', woman_good_percent='%s' where cid='%s'" % (
                res_list[0], res_list[2], res_list[3], res_list[4], item[0])
            try:
                cursor.execute(update_sql)
                db.commit()
                pass
            except Exception as e:
                print(str(e))
                pass
            num += 1
            # break
            pass
        pass

    @staticmethod
    def doing_calculation(relation_res):
        # third is user_name
        # fifth is evaluation
        total = len(relation_res)
        if total == 0:
            return [0, 0, 0, 0, 0]
        # good_percent
        num_1 = 0
        # bad_percent
        num_0 = 0
        # neutral_percent
        num_others = 0
        # man_good_percent  woman_good_percent
        num_man = 0
        num_woman = 0
        for item in relation_res:
            evaluation = item[4]
            user_name = item[2]
            if evaluation == 1:
                num_1 += 1
                user_sql = "select sex from user where name='%s'" % (user_name)
                cursor.execute(user_sql)
                sex = cursor.fetchone()
                if sex == None:
                    continue
                if sex[0] == "男":
                    num_man += 1
                    pass
                elif sex[0] == "女":
                    num_woman += 1
                    pass
                pass
            elif evaluation == 0:
                num_0 += 1
                pass
            elif evaluation == -1:
                num_others += 1
            pass
        if num_1 == 0:
            num_man = 0
            num_woman = 0
            pass
        elif num_1 != 0:
            num_woman = num_woman / num_1
            num_man = num_man / num_1
            pass
        res_list = [num_1 / total, num_0 / total, num_others / total, num_man, num_woman]
        return res_list


if __name__ == "__main__":
    start = datetime.datetime.now()
    queue = Queue(row_distance)
    producer = Producer(queue)
    consumer = Consumer(queue)
    producer.start()
    consumer.start()

    producer.join()
    consumer.join()
    end = datetime.datetime.now()

    print("运行时间", (end - start).seconds)

    calculation = CalculationEvaluation()
    calculation.calculation()

    # db.close()
