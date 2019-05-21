// // Create and Deploy Your First Cloud Functions
// // https://firebase.google.com/docs/functions/write-firebase-functions
//
// exports.helloWorld = functions.https.onRequest((request, response) => {
//  response.send("Hello from Firebase!");
// });

const functions = require('firebase-functions');
const admin = require('firebase-admin');
admin.initializeApp();

exports.sendNotification = functions.firestore.document("users/{user_id}/notifications/{notification_id}") //receiver
    .onWrite((change, context) => {
        //so each time a notification is added to each user, this function will be triggered 

        const user_id = context.params.user_id;          //receiver id
        const notification_id = context.params.notification_id; 

        console.log("user id:" + user_id + "| Notification id: " + notification_id);

        return admin.firestore().collection("users").doc(user_id).collection("notifications").doc(notification_id)
            .get().then(queryResult => {

                const from_user_id = queryResult.data().from; //sender id
                const from_message = queryResult.data().message; //sender message

                console.log("From user id: "+ from_user_id + "| message " + from_message);

                const from_data = admin.firestore().collection("users").doc(from_user_id).get(); //gets senders data
                const to_data = admin.firestore().collection("users").doc(user_id).get(); //gets receivers data

               return Promise.all([from_data, to_data]).then(result => {//[index0, index1]
                    const from_name = result[0].data().name;//sender name..index0 result0
                    const to_name = result[1].data().name;//receiver name
                   const token_id = result[1].data().tokenId;//receiver token id

                   console.log("from name" + from_name + "| to name " + to_name + "receiver token id" + token_id);
                    

                    const payload = {
                        notification: {
                            title: "Notification From: " + from_name,
                            body: from_message,
                            icon: "default",
                            priority: "high",
                            click_action: ".notification.target.senc"
                        },
                        data: {
                            message: from_message,
                            from_user: from_name
                        }
                    
                        
                   };
           

                    return admin.messaging().sendToDevice(token_id, payload)//receivers token id
                            .then(function (response) {
                                console.log("Successfully sent message:", response);
                                console.log(response.results[0].error);
                            })
                            .catch(function (error) {
                                console.log("Error sending message:", error);
                            });
                        

                });
            });

    });
