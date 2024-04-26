
import json
from pymongo import MongoClient
from flask import Flask
from flask import request
from flask.json import jsonify

app = Flask(__name__)

client = MongoClient("mongodb+srv://SamariOglesby:CactusSam21@cluster.cqcy0rq.mongodb.net/?retryWrites=true&w=majority&appName=Cluster")

db = client["CISC349"]


# A welcome message to test our server
@app.route('/')
def index():
    return "<h1>Welcome to our server 123 !!</h1>"


# Add user
@app.route('/add', methods=['POST'])
def add():
    collection = db["users"]
    request_data = request.get_json()
    username = request_data['username']
    password = request_data['password']
    data = { 'username': username, 'password': password }
    _id = collection.insert_one(data)
    return json.dumps({'id' : str(_id.inserted_id)})


# Login for Users with an account
@app.route('/login', methods=['POST'])
def login():
    collection = db["users"]
    request_data = request.get_json()
    username = request_data['username']
    password = request_data['password']
    
    user = collection.find_one({'username': username, 'password': password})
    
    if user:
        success = True
    else:
        success = False

    return json.dumps({'login': success})



# Update user
@app.route('/update', methods=['POST'])
def update():
    collection = db["users"]
    request_data = request.get_json()
    id = request_data['_id']
    name = request_data['name']
    address = request_data['address']
    phone = request_data['phone']
    comments = request_data['comments']
    print(f'ID: {id}, Name: {name}, Address: {address}, Phone: {phone}, Comments: {comments}')
    filter = {'_id' : id}
    newvalues = { "$push": {'comments':comments}}

    _id = collection.update_one(filter, newvalues)
    return json.dumps({'_id' : id})



# Select All users
@app.route('/all', methods=['GET'])
def all():
    collection = db["users"]
    customers = list(collection.find())
    # we need to convert _id to str.
    return json.dumps(customers, default=str)



if __name__ == "__main__":
    app.run(host="0.0.0.0", port=5000)