import sys
import psycopg2

if (len(sys.argv) != 3 or int(sys.argv[1]) == int(sys.argv[2]) or int(sys.argv[1]) < 0 or int(sys.argv[2] < 0)):
	print("Please enter exactly two unique positive integers separated by a space.")

else:
	if (int(sys.argv[1]) < int(sys.argv[2])):
		min = int(sys.argv[1])
		max = int(sys.argv[2])

	elif (int(sys.argv[1]) > int(sys.argv[2])):
		min = int(sys.argv[2])
		max = int(sys.argv[1])

	# connect to database
	try:
		conn = psycopg2.connect(database="tcount", user="postgres", password="pass", host="localhost", port="5432")
	except:
		print("Could not connect to database")

	# process query
	cur = conn.cursor()
	cur.execute("SELECT word, count FROM tweetwordcount WHERE count >= {0} AND count <={1} ORDER BY count;".format(min, max))
	
	records = cur.fetchall() 

	# show results to user

	count1 = 0
	count2 = 0
	if not (records[0][1]):
		print("No words with that count range.")
	else:
		for rec in records:
			print("The word \"{0}\" appears {1} times in the database.".format(rec[0], rec[1]))

	conn.commit()
	conn.close() 
