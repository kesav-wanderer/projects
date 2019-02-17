import sys
import psycopg2

if (len(sys.argv) != 2):
	print("Please enter one word as paramater for this program.")
else:
	# take the word
	word = sys.argv[1]

	# check for apostrophe
	tmpword = word
	count = 0
	while (count < len(tmpword)):
		if (tmpword[count] == "'" and tmpword[count+1] != "'" and tmpword[count-1] != "'"):
			tmpword = tmpword[:count] + "'" + tmpword[count:]
			count += 1
			continue
		count += 1

	# connect to Postgres database
	try:
		conn = psycopg2.connect(database="tcount", user="postgres", password="pass", host="localhost", port="5432")
	except:
		print("Could not connect to database")

	# process query
	cur = conn.cursor()
	cur.execute("SELECT word, count FROM tweetwordcount WHERE word = '{0}';".format(tmpword)) 
	
	records = cur.fetchall()

	# show user the results
	
	if (records):
		 print("The word \"{0}\" appears {1} times in this database.".format(records[0][0], records[0][1]))

	else:
		print("The word \"{0}\" appears 0 times in the database".format(tmpword))
		
	conn.commit()
	conn.close()
