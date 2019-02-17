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



#creating features
def create_word_features(words):
    useful_words = [word for word in words if word not in stopwords.words("english")]
    my_dict = dict([(word, True) for word in useful_words])
    return my_dict




#loading the dataset
with open("negR","r") as f:
	neg=f.readlines()

with open("posR","r") as f:
	pos=f.readlines()


with open("train.csv","r") as f:
	d2=f.readlines()




neg_reviews=[]
pos_reviews=[]

neg_reviews1=[]
pos_reviews1=[]

neg_reviews2=[]
pos_reviews2=[]

with open("negS","r") as f:
	text=f.readlines()

for review in text:
	review=re.sub(r'[^\w ]', '',review)
	review=re.sub( '\s+', ' ', review ).strip()
	neg_reviews2.append((create_word_features(review.split(" ")),"negative"))

print "Finished neg folder"

with open("posS","r") as f:
	text=f.readlines()

for review in text:
	review=re.sub(r'[^\w ]', '',review)
 	review=re.sub( '\s+', ' ', review ).strip()
 	pos_reviews2.append((create_word_features(review.split(" ")),"positive"))

print "Finished pos folder"



for line in d2:
	review=line[1:]
	if line[0] == '0' :
		review=re.sub(r',', ' ',review)
		review=re.sub(r'[^\w ]', '',review)
	 	review=re.sub( '\s+', ' ', review ).strip()
	 	neg_reviews1.append((create_word_features(review.split(" ")),"negative"))
	else:
		review=re.sub(r',', ' ',review)
		review=re.sub(r'[^\w ]', '',review)
	 	review=re.sub( '\s+', ' ', review ).strip()
	 	pos_reviews1.append((create_word_features(review.split(" ")),"positive"))


print "Finished train.csv"



for review in neg:
	review=re.sub(r'[^\w ]', '',review)
	review=re.sub( '\s+', ' ', review ).strip()
	neg_reviews.append((create_word_features(review.split(" ")),"negative"))


print "Finished neg_reviews"

for review in pos:
	review=re.sub(r'[^\w ]', '',review)
	review=re.sub( '\s+', ' ', review ).strip()
	pos_reviews.append((create_word_features(review.split(" ")),"positive"))

print "Finished pos_reviews"



neg_reviews.extend(neg_reviews1)
pos_reviews.extend(pos_reviews1)



neg_reviews.extend(neg_reviews2)
pos_reviews.extend(pos_reviews2)

print "Array extended"

pos_len=len(pos_reviews)
neg_len=len(neg_reviews)

pos_split=(int)(0.8 * pos_len)
neg_split=(int)(0.8 * neg_len)


print pos_len
print neg_len

print pos_split
print neg_split


train_set = neg_reviews[:neg_split]+pos_reviews[:pos_split]
test_set =  neg_reviews[neg_split:] + pos_reviews[pos_split:]
print(len(train_set),  len(test_set))

print "Train and Test split"


numpy.random.shuffle(train_set)
numpy.random.shuffle(test_set)

print "Data shuffled"

import pickle
f = open('train.pickle', 'wb')
pickle.dump(train_set, f)
f.close()

f = open('test.pickle', 'wb')
pickle.dump(test_set, f)
f.close()


# #print "Building model  : SVM"
# classifier = SklearnClassifier(LinearSVC(), sparse=True)
# classifier.train(train_set)

# #print "Building model : Maximum Entropy"
# # classifier=nltk.classify.maxent.MaxentClassifier.train(train_set, 'GIS', trace = 0, encoding = None, labels = None, gaussian_prior_sigma = 0, max_iter = 20)

# # print "Building Model : Naive Bayes"	
# # classifier = NaiveBayesClassifier.train(train_set)

# print "Model Build"


# print "Accuracy :"
# accuracy = nltk.classify.util.accuracy(classifier, test_set)
# print(accuracy * 100)

# import pickle
# f = open('mentropy_classifier.pickle', 'wb')
# pickle.dump(classifier, f)
# f.close()

# print "model saved"