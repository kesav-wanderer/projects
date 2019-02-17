import nltk
import sys
import re
import os
import numpy

import nltk.classify.util
from nltk.classify import NaiveBayesClassifier
from nltk.corpus import movie_reviews
from nltk.corpus import stopwords
from nltk.tokenize import word_tokenize

#creating features
def create_word_features(words):
    useful_words = [word for word in words if word not in stopwords.words("english")]
    my_dict = dict([(word, True) for word in useful_words])
    return my_dict


print "Enter the model no : 1. Naive Bayes Classifier    2. SVM   3. Maximum Entropy"

i=raw_input()
name=""

if i == "1":
	name='mentropy_classifier.pickle'
elif i == "2":
	name='svm_classifier.pickle'
else:
	'naive_classifier.pickle'

#loading the model
import pickle
f = open(name, 'rb')
classifier = pickle.load(f)
f.close()


print "Enter the review"
review=raw_input()


#review="the movie is no moral sense inept and often lethally dull  disintegrated plot worst" 

review=re.sub(r'[^\w ]', '',review)
review=re.sub( '\s+', ' ', review ).strip()


print review

words = create_word_features(review.split(" "))

print classifier.classify(words)