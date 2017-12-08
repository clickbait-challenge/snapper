from os.path import join
from sklearn import preprocessing,svm
from sklearn.pipeline import Pipeline
import nltk
import numpy
import subprocess
import time
import utility

import json
from pprint import pprint
import unicodedata

inputfilename='StopClickbaitPaperDBFeatures.txt'

with open(join('experimentdata/TweetCorpus',inputfilename)) as data_file:
	content = data_file.readlines()
	#content = [content[0]] #only keep first line

post_f = open(join('experimentdata/TweetCorpus',inputfilename) + '_vectors2.txt','w')

for line_ind in range(len(content)):
	print line_ind
	line=content[line_ind]
#	line.decode('ISO 8859-1').encode('utf-8')
	line = nltk.re.sub('\xa3', '', line)
	line = nltk.re.sub('\xa0', '', line)
	line = nltk.re.sub('\x85', '-', line)
	line = nltk.re.sub('\x96', '-', line)
	line = nltk.re.sub('\x97', '-', line)
	line = nltk.re.sub('\x46', '\'', line)
	line = nltk.re.sub('\x91', '\'', line)
	line = nltk.re.sub('\x92', '\'', line)
	line = nltk.re.sub('\x93', '\'', line)
	line = nltk.re.sub('\x94', '\'', line)
	line = nltk.re.sub('\x95', '-', line)
	line = nltk.re.sub('\x80', '$', line)
	line = nltk.re.sub('\xb0', '$', line)
	line = nltk.re.sub(r'[^\x00-\x7F]+', ' ', line)
	#data = json.loads(line)
	data=line.split('\t') #this for tweets
	#data = [' ', line] #this for YouTube
	postText=data[1]
	print postText
	splitPost=postText.split(' ')
	if len(splitPost)>50:
		postText=' '.join(splitPost[0:50])
		print 'cut to:'
		print postText

	postText = postText.decode('utf-8')  # .encode('ascii', 'ignore')
	if len(postText)<5:
		post_vector = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
	else:
		post_vector = utility.create_vector(postText)
	post_f.write(' '.join(map(str, post_vector))+'\n')
post_f.close()