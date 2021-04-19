from flask import Flask, send_from_directory
from flask import jsonify
from flask import request
from flask_pymongo import PyMongo
from pdf_generator import *

app = Flask(__name__)

app.config['MONGO_DBNAME'] = 'Votes'
app.config['MONGO_URI'] = 'mongodb://localhost:27017/VoteDB'

mongo = PyMongo(app)


@app.route('/votes', methods=['GET'])
def get_all_votes():
    star = mongo.db.Votes
    output = []
    for s in star.find():
        output.append({'name': s['name'], 'candidateId': s['candidateId']})
    return jsonify({'result': output})


@app.route('/votebyname', methods=['GET'])
def get_votes_by_name():
    name = request.args.get("name")
    name = name.capitalize()
    star = mongo.db.Votes
    agg_result = star.find({
        "name": name
    }).count();

    return jsonify({'name': name,
                    'total_votes': agg_result
                    })


@app.route('/totalvotes', methods=['GET'])
def get_total_votes():
    star = mongo.db.Votes
    output = mongo.db.Votes.aggregate([
        {"$group": {"_id": "$name", 'count': {"$sum": 1}}}
    ])
    res_list = []
    for i in output:
        d = {'name': i["_id"], "total_votes": i["count"]}
        res_list.append(d)

    return jsonify(res_list)


@app.route('/add', methods=['POST'])
def add_star():
    star = mongo.db.Votes
    name = request.json['name']
    candidateId = request.json['candidateId']

    if name.lower()== "ram" or name.lower()== "sham":
        if name.lower()== "ram" and not candidateId == "1":
            return jsonify("Please provide candidateId as 1 for Ram")
        if name.lower()== "sham" and not candidateId == "2":
            return jsonify("Please provide candidateId as 2 for Sham")
        name = name.capitalize()
        star_id = star.insert({'name': name, 'candidateId': candidateId})
        new_star = star.find_one({'_id': star_id})
        if new_star:
            return jsonify("Successfully Voted for {}".format(name))
        else:
            return jsonify("Something went wrong")


@app.route('/getpdfresult', methods=['GET'])
def get_pdf_result():
    star = mongo.db.Votes
    output = mongo.db.Votes.aggregate([
        {"$group": {"_id": "$name", 'count': {"$sum": 1}}}
    ])
    res_list = []
    for i in output:
        d = {'name': i["_id"], "total_votes": i["count"]}
        res_list.append(d)
    create_pdf(res_list);
    return send_from_directory(directory='static',
                               filename='table_results.pdf',
                               mimetype='application/pdf',
                               as_attachment=True)


if __name__ == '__main__':
    app.run(debug=True)
