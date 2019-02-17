import pickle
import nltk
import sys
import re
import os
import numpy

import nltk.classify.util
from nltk.classify import NaiveBayesClassifier, MaxentClassifier, SklearnClassifier
import csv
from sklearn.svm import LinearSVC, SVC
from nltk.corpus import stopwords
from nltk.tokenize import word_tokenize




f = open('train.pickle', 'rb')
train_set=pickle.load(f)
f.close()

f = open('test.pickle', 'rb')
test_set=pickle.load(f)
f.close()


print len(train_set)
print len(test_set)


print "Building model : Maximum Entropy"
classifier=nltk.classify.maxent.MaxentClassifier.train(train_set, 'GIS', trace = 0, encoding = None, labels = None, gaussian_prior_sigma = 0, max_iter = 20)


import pickle
f = open('mentropy_classifier.pickle', 'wb')
pickle.dump(classifier, f)
f.close()


print "Accuracy :"
accuracy = nltk.classify.util.accuracy(classifier, test_set)
print(accuracy * 100)

print "Building model : Naive Bayes"
classifier = NaiveBayesClassifier.train(train_set)


import pickle
f = open('naive_classifier.pickle', 'wb')
pickle.dump(classifier, f)
f.close()


print "Accuracy :"
accuracy = nltk.classify.util.accuracy(classifier, test_set)
print(accuracy * 100)

print "Building model : SVM"
classifier = SklearnClassifier(LinearSVC(), sparse=True)
classifier.train(train_set)


import pickle
f = open('svm_classifier.pickle', 'wb')
pickle.dump(classifier, f)
f.close()


print "Accuracy :"
accuracy = nltk.classify.util.accuracy(classifier, test_set)
print(accuracy * 100)


print "model saved"