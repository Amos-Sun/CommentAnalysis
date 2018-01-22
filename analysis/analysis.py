#!usr/bin/python3
# -*- coding -*-
# python3 use pymysql

import pymysql
import urllib.parse
from snownlp import SnowNLP

db = pymysql.connect("localhost", "root", "root", "comment_analysis")
cursor = db.cursor()


def db_handler():
    sql = "select cid from relation group by cid"
    cursor.execute(sql)
    cid_list = cursor.fetchall()
    for item in cid_list:
        sql = "select * from relation where cid='%s';" % item
        cursor.execute(sql)
        relation_list = cursor.fetchall()
        for relation in relation_list:
            content = relation[3]
            content = urllib.parse.unquote(content)
            if len(content) <= 0:
                continue
                pass
            mention = analysis(content)
            sql_1 = "update relation set evaluation='%d' where id='%d';" % (mention, relation[0])
            try:
                cursor.execute(sql_1)
                db.commit()
                print("update success ", relation[0])
            except:
                print("failed update")
                db.rollback()
            pass
    db.close()


def analysis(text):
    s = SnowNLP(text)
    mention = s.sentiments
    if mention > 0.6:
        return 1
    elif mention < 0.4:
        return -1
    else:
        return 0


def count_have_calculated():
    sql = "select count(*) from relation where evaluation != 0"
    cursor.execute(sql)

    result = cursor.fetchall()
    print(result)


if __name__ == "__main__":
    count_have_calculated()
# db_handler()
