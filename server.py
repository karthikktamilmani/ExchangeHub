from flask import Flask
import pymysql
import os
from flask import jsonify
from flask import flash,request
from flaskext.mysql import MySQL
from PIL import Image
import base64
import datetime
#from werkzeug import generate_password_hash, check_password_hash

app = Flask(__name__)

mysql = MySQL()

app.config['MYSQL_DATABASE_USER'] = 'myuser'
app.config['MYSQL_DATABASE_PASSWORD'] = 'mypass'
app.config['MYSQL_DATABASE_DB'] = 'ExchangeHub'
app.config['MYSQL_DATABASE_HOST'] = '3.133.81.209'
mysql.init_app(app)


@app.route('/')
def home():
	return 'Hello world!'

@app.route('/actors', methods = ['POST'])
def getActors():
	try:
		conn = mysql.connect()
		cur = conn.cursor(pymysql.cursors.DictCursor)
		cur.execute("SELECT * FROM actors;")
		rows=cur.fetchall()
		resp=jsonify(rows)
		resp.status_code=200
		return resp
	except Exception as e:
		print(e)
	finally:
		cur.close()
		conn.close()




@app.route('/addingPost', methods = ['POST'])
def addPost():
    prod_name = 'Macbook'
    Description = '2 years old'
    prod_location = '1.2.35'
    Looking_for = 'iphone;ipad;NikeShoes'
    with open('/Users/parthpanchal7/Documents/Buddha-1.jpg','rb') as f:
        image_data = f.read()
    free = 'true'
    Price = '0'
    traded = 'false'
    user_id = '1'
    sql = "INSERT INTO PRODUCT(image) VALUES(%s)"
    data = (image_data)
    conn = mysql.connect()
    cursor = conn.cursor()
    cursor.execute(sql, data)
    conn.commit()
    return "Image added Successfully"

@app.route('/updateProfile',methods=['POST'])
def updateProfile():
    flag = 0
    try:
        print("here")
        imageFile = request.files['image']
        _json = request.json
        _image = _json["image"]
        imageFile = base64.decodestring(_image)
        img = Image.open(imageFile)
        #_name = _json['name']
        #_email = _json['email']
        #_password = _json['pwd']
        #imageFile.save(os.path.join(app.config['/ImageStorage'], 'FirstImage'))
        img.save("concave.jpg")
        if imageFile :
            return "COOL"
        else:
            return "EMPTY"
    except Exception as e :
        print(e)
    return "DONE"

@app.route('/retrieveSettings',methods=['POST'])
def retrieveNameandEmail():
  userID = request.form['userID']
  try:
      sql = "select * from USERS where user_id=%s"
      conn = mysql.connect()
      cursor = conn.cursor()
      cursor.execute(sql,userID)
      rows = cursor.fetchall()

      for row in rows:
          Name = row[1]
          Email = row[3]

      jsonData = jsonify(name=Name,email=Email)
      print(jsonData)
      return jsonData
  except Exception as e:
        print(e)


@app.route('/UpdatePassword',methods=['POST'])
def UpdatePassword():
 userID = request.form['userID']
 updatedPassword = request.form['Password']
 print(userID)
 print(updatedPassword)
 sql = 'UPDATE USERS SET pass=%s where user_id=%s'
 conn = mysql.connect()
 cursor = conn.cursor()
 data = (updatedPassword,userID)
 state = cursor.execute(sql, data)
 conn.commit()
 if state:
     response = jsonify(message="password Updated Successfully")
 return response

@app.route('/RetrievePassword',methods=['POST'])
def retrievePassword():
    userID = request.form['UserID']
    try:
        sql = "select * from USERS where user_id=%s"
        conn = mysql.connect()
        cursor = conn.cursor()
        cursor.execute(sql, userID)
        rows = cursor.fetchall()

        for row in rows:
            password = row[2]

        print(password)
        jsonData = jsonify(Password = password)
        print(jsonData)
        return jsonData
    except Exception as e:
        print(e)

@app.route('/uploadImage',methods=['POST'])
def uploadImage():
 print("gamma")
 #image_name = request.form['name']
 image_data = request.form['image']
 description = request.form['desc']
 UserID = request.form["UserID"]
 print(image_data)
 print(description)
 print(UserID)
 lookingFor = "LinuxBox;GameConsole"

 image_data = base64.b64decode(image_data)
 filename =  'static/Images/'+UserID + ".jpeg"
 filename = str(filename)
 description = str(description)
 UserID = str(UserID)
 lookingFor = str(lookingFor)
 print(filename)
 if description != "Empty String":
     sqlStatement = 'UPDATE USER_DETAILS SET description=%s, USER_PROFILE_IMAGE=%s, looking_for=%s where user_id=%s'
     data = (description,filename,lookingFor,UserID)
 if description == "Empty String":
     sqlStatement = 'UPDATE USER_DETAILS SET USER_PROFILE_IMAGE=%s,looking_for=%s where user_id=%s'
     data= (filename,lookingFor,UserID)

 conn = mysql.connect()
 cursor = conn.cursor()
 print(sqlStatement)
 print(data)
 state = cursor.execute(sqlStatement, data)
 conn.commit()
 if state:
     print("updation successful")



 with open(filename,'wb') as f:
  f.write(image_data)

 resp = "talking..."
 return resp

@app.route('/populateData',methods=['POST'])
def populateData():
 try:
    UserID = request.form["userID"]
    print(UserID)
    sqlStatement = "Select * from USER_DETAILS WHERE user_id=%s"
    conn = mysql.connect()
    cursor = conn.cursor()
    cursor.execute(sqlStatement,UserID)
    rows = cursor.fetchall()
    for row in rows:
        description = row[1]
        USER_PROFILE_IMAGE = row[4]
        print(description)
        print(USER_PROFILE_IMAGE)
    print(description)
    print(USER_PROFILE_IMAGE)
    with open(USER_PROFILE_IMAGE, "rb") as img_file:
        my_image = str(img_file.read())
        # my_string = str(my_string)
    print(my_image)
    jsonData = jsonify(image=USER_PROFILE_IMAGE,desc=description)
    return jsonData
 except Exception as e:
    print(e)
#Add Post Fuctionality Starts
@app.route('/AddPostModule',methods=['POST'])
def addPostModule():
    try:
      UserId = request.form["userID"]
      Product = request.form["Product"]
      Description = request.form["Description"]
      Price = request.form["Price"]
      Category = request.form["Category"]
      image_data = request.form['image']
      image_data = base64.b64decode(image_data)
      #Product ID GENERATION
      ImageName = str(datetime.datetime.now().date()) + '_' + str(datetime.datetime.now().time()).replace(':', '.')
      filename = 'static/ProductImages/' + ImageName + ".jpeg"
      sql = "INSERT INTO PRODUCT(prod_name,Prod_Description,Price,user_id,Image,Category) VALUES(%s,%s,%s,%s,%s,%s)"
      data = (Product,Description,Price,UserId,ImageName,Category)
      conn = mysql.connect()
      cursor = conn.cursor()
      cursor.execute(sql, data)
      conn.commit()
      with open(filename, 'wb') as f:
          f.write(image_data)
      print(Category)
     #Database Entry

      return jsonify(response = "Product Added")

    except Exception as e:
      print(e)

if __name__ == '__main__':
	app.run(host='0.0.0.0', port=80)
