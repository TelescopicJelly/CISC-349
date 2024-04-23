
import json
from pymongo import MongoClient
from flask import Flask
from flask import request
from flask.json import jsonify
from datetime import datetime
import pymongo

app = Flask(__name__,
            static_folder='images')

client = MongoClient("mongodb+srv://SamariOglesby:CactusSam21@cluster.cqcy0rq.mongodb.net")

db = client["CISC349"]


# A welcome message to test our server
@app.route('/')
def index():
    return "<h1>Welcome to our server 123 !!</h1>"


@app.route('/image', methods=['POST'])
def image_save():
    collection = db["images"]
    content = request.get_json()
    name = content.get('name')
    comment = content.get('comment')

    date_time_str = content.get('dateTime')
    date_time = datetime.strptime(date_time_str, "%Y-%m-%d %H:%M:%S")
    image_data = content.get('image')
    _id = collection.insert_one({'name': name, 'comment': comment, 'dateTime': date_time, 'image': image_data})
    return json.dumps({'id' : str(_id.inserted_id)})

@app.route('/images', methods=['GET'])
def image_list():
    collection = db["images"]
    images = list(collection.find())
    print(f"Got {len(images)} images.")
    return json.dumps(images, default=str)


if __name__ == "__main__":
    app.run(host="0.0.0.0", port=5000)
