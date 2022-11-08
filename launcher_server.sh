netid=pmt093020

PROJDIR=$HOME/CS6378/Project2

CONFIG=$PROJDIR/testconfig.txt

n=1

cat $CONFIG | sed -e "s/#.*//" | sed -e "/^\s*$/d" | 
( 
    read i
    echo $i
    while read line
    do 
    	host=$( echo $line | awk '{ print $1 }' )

	ssh $netid@$host java -classpath $PROJDIR Project2 $n &

	n=$(( n + 1 ))
    done
)
