while read line; 
do 
    echo $line;
#    if grep -iq "$line" data/Titanic.txt; 
#    then
#        echo `grep -i "$line" data/Titanic.txt|wc -l`
#    fi; 
done 
< all_cleared.txt
