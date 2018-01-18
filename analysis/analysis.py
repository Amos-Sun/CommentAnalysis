#!usr/bin/python3
# -*- coding -*-
# python3 use pymysql

import pymysql
import urllib.parse
from snownlp import SnowNLP


def db_handler():
    sql = "select cid from relation group by cid"
    db = pymysql.connect("localhost", "root", "root", "comment_analysis")
    cursor = db.cursor()
    cursor.execute(sql)
    cid_list = cursor.fetchall()
    for item in cid_list:
        sql = "select * from relation where cid='%s';" % item
        cursor.execute(sql)
        relation_list = cursor.fetchall()
        for relation in relation_list:
            print(relation)
            content = relation[3]
            content = urllib.parse.unquote(content)
            mention = analysis(content)
            sql_1 = "update relation set evaluation='%d' where id='%d';" % (mention, relation[0])
            try:
                cursor.execute(sql_1)
                db.commit()
            except:
                print("failed update")
                db.rollback()
            pass
        break
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


if __name__ == "__main__":
    print("test")
    db_handler()
