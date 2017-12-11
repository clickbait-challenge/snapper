from os.path import join
from sklearn import preprocessing,svm
from sklearn.pipeline import Pipeline
import nltk
import numpy
import subprocess
import time
import utility
import sys, getopt

import json
from pprint import pprint
import unicodedata

def main(argv):
	content=''


	#print(argv[0])
	args = sys.argv[1:]
	for arg in args:
		content += " " + arg

	#print(content)

	content.decode('ISO 8859-1').encode('utf-8')
	content = nltk.re.sub('\xa3', '', content)
	content = nltk.re.sub('\xa0', '', content)
	content = nltk.re.sub('\x85', '-', content)
	content = nltk.re.sub('\x96', '-', content)
	content = nltk.re.sub('\x97', '-', content)
	content = nltk.re.sub('\x46', '\'', content)
	content = nltk.re.sub('\x91', '\'', content)
	content = nltk.re.sub('\x92', '\'', content)
	content = nltk.re.sub('\x93', '\'', content)
	content = nltk.re.sub('\x94', '\'', content)
	content = nltk.re.sub('\x95', '-', content)
	content = nltk.re.sub('\x80', '$', content)
	content = nltk.re.sub('\xb0', '$', content)
	content = nltk.re.sub(r'[^\x00-\x7F]+', ' ', content)
	#data = json.loads(line)
	#data=line.split('\t') #this for tweets
	#data = [' ', line] #this for YouTube
	postText=content.replace('\n','')
	#print postText
	splitPost=postText.split(' ')
	if len(splitPost)>50:
		postText=' '.join(splitPost[0:50])
		#print 'cut to:'
		#print postText
		postText = postText.decode('utf-8')  # .encode('ascii', 'ignore')
	if len(postText)<5:
		post_vector = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
	else:
		post_vector = utility.create_vector(postText)
	#post_f.write(' '.join(map(str, post_vector))+'\n')
	print post_vector
	#post_f.close()

if __name__=="__main__":
	main(sys.argv[1:])
