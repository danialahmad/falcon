#!/bin/bash
db_user=falcon
db_password=phefrebre796ayuf
sql_location="`dirname \"$0\"`"
file_name=falcon

db_name=ignore
if [ -z "$1" ]
then
	db_name=${file_name}
else
	db_name=$1
fi


echo drop database if exists ${db_name} | mysql -u ${db_user} -p${db_password}
echo Database drop. Done.
echo create database ${db_name} | mysql -u ${db_user} -p${db_password}
echo Database create. Done.
mysql -u ${db_user} -p${db_password} ${db_name} < ${sql_location}/${file_name}-structure.sql
echo Structure create. Done.

if [ "$file_name" == "$db_name" ]
then
	mysql -u ${db_user} -p${db_password} ${db_name} < ${sql_location}/${file_name}-data.sql
	echo Data populate. Done.
fi
	

echo Done

