FROM mongo

COPY init.json /init.json

CMD mongoimport --host foodDBService --db foodDB --collection food --type json --file /init.json --jsonArray
