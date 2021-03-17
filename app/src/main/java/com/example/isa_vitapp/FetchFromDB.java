package com.example.isa_vitapp;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Source;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class FetchFromDB {

    private String member_name;
    private String instagram_link;
    private String linkedin_link;
    private String github_link;

    private String mobile;
    private String pemail;
    private String vemail;
    private String reg_number;

    private String room_number;
    private String position;

    private String photo_link;
    public static int total_board_members;

    public static Map<String, Object> position_name = new HashMap<>();
    Map<String, Object> member_details = new HashMap<>();

    public static FetchFromDB[] members = new FetchFromDB[15];

    public void getBoardMemberNames() {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("Board").document("Names");
        Log.d("Thread", "Executing board member names");

        // Source can be CACHE, SERVER, or DEFAULT.
        Source source = Source.DEFAULT;

        // Get the document, forcing the SDK to use the offline cache
        docRef.get(source).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    // Document found in the offline cache
                    DocumentSnapshot document = task.getResult();
                    assert document != null;
                    Log.d("TAG", "Cached document data: " + document.getData());

                    position_name = document.getData();

                } else {
                    Log.d("TAG", "Cached get failed: ", task.getException());
                }
            }
        });

    }

    public void getBoardMemberDetails(String name) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("Board Data").document(name);

        // Source can be CACHE, SERVER, or DEFAULT.
        Source source = Source.CACHE;

        // Get the document, forcing the SDK to use the offline cache
        docRef.get(source).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    // Document found in the offline cache
                    DocumentSnapshot document = task.getResult();
                    assert document != null;
                    Log.d("TAG", "Cached document data: " + document.getData());

                    member_details = document.getData();

                    try {
                        Log.d("Thread", "Executing board member details" + member_details.get("Name").toString());

                        member_name = member_details.get("Name").toString();
                        position = member_details.get("Position").toString();
                        instagram_link = member_details.get("Instagram Link").toString();
                        linkedin_link = member_details.get("Linkedin Link").toString();
                        github_link = member_details.get("Github Link").toString();
                        mobile = member_details.get("Contact Number").toString();
                        pemail = member_details.get("Personal email").toString();
                        vemail = member_details.get("VIT Email").toString();
                        reg_number = member_details.get("Registration Number").toString();
                        room_number = member_details.get("Room Number").toString();
                        photo_link = member_details.get("Photo Link").toString();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {
                    Log.d("TAG", "Cached get failed: ", task.getException());
                }
            }
        });

    }

    public String getMember_name() {
        return member_name;
    }

    public String getInstagram_link() {
        return instagram_link;
    }

    public String getLinkedin_link() {
        return linkedin_link;
    }

    public String getGithub_link() {
        return github_link;
    }

    public String getMobile() {
        return mobile;
    }

    public String getPemail() {
        return pemail;
    }

    public String getVemail() {
        return vemail;
    }

    public String getReg_number() {
        return reg_number;
    }

    public String getRoom_number() {
        return room_number;
    }

    public String getPosition() {
        return position;
    }

    public String getPhoto_link() {
        return photo_link;
    }

    public static Map<String, Object> getPosition_name() {
        return position_name;
    }

    public Map<String, Object> getMember_details() {
        return member_details;
    }

    public void getData() {

        ArrayList<String> board_members_list = new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        DocumentReference docRef = db.collection("Board").document();
        docRef.get().

                addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document != null) {

                            } else {
                                Log.d("LOGGER", "No such document");
                            }
                        } else {
                            Log.d("LOGGER", "get failed with ", task.getException());
                        }
                    }
                });
    }


}

