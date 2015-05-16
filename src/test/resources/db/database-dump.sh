#!/bin/bash
db_host=localhost
db_user=falcon
db_password=phefrebre796ayuf
db_name=falcon
output_path="`dirname \"$0\"`"

mysqldump -d -h $db_host -u $db_user -p${db_password} ${db_name} 2> /dev/null | grep -v "Dump completed on" > ${output_path}/${db_name}-structure.sql
echo Structure dump. Done.
mysqldump -u $db_user -p${db_password} --no-create-db --no-create-info --skip-extended-insert --complete-insert ${db_name}  2> /dev/null | grep -v "Dump completed on" > ${output_path}/${db_name}-data.sql
echo Data dump. Done.
