from __future__ import absolute_import, print_function, unicode_literals
from collections import Counter
from streamparse.bolt import Bolt

import psycopg2


class WordCounter(Bolt):

	def initialize(self, conf, ctx):
        	self.counts = Counter()
       

	def process(self, tup):
		word = tup.values[0]

		postWord = "'" + word + "'"		

		conn = psycopg2.connect(database="tcount", user="postgres", password="pass", host="localhost", port="5432")

		if (self.counts[word]<1):
			cur = conn.cursor()
			cur.execute("INSERT INTO tweetwordcount (word, count) VALUES({0}, {1});".format(postWord, 1))
			conn.commit()
			conn.close()
		else:
			cur = conn.cursor()
			cur.execute("UPDATE tweetwordcount SET count={0} WHERE word={1};".format(self.counts[word]+1, postWord))
			conn.commit()
			conn.close()

      
        

        # Increment the local count
		self.counts[word] += 1
		self.emit([word, self.counts[word]])

        # Log the count - just to see the topology running
		self.log('%s: %d' % (word, self.counts[word]))
