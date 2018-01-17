# -*- coding=utf-8 -*-
from snownlp import SnowNLP
import sys


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
    text = sys.argv[1]
    mention = analysis(text)
    print(mention)
