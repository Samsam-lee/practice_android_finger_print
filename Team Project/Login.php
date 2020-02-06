<?php
    $con = mysqli_connect("localhost", "yjlsh", "yj135246!", "yjlsh");
    mysqli_query($con,'SET NAMES utf8');

    $userID = $_POST["userID"];
    $userPW = $_POST["userPW"];
    
    $statement = mysqli_prepare($con, "SELECT * FROM USER WHERE userID = ? AND userPW = ?");
    mysqli_stmt_bind_param($statement, "ss", $userID, $userPW);
    mysqli_stmt_execute($statement);


    mysqli_stmt_store_result($statement);
    mysqli_stmt_bind_result($statement, $userName, $userID, $userPW);

    $response = array();
    $response["sign_up"] = false;
 
    while(mysqli_stmt_fetch($statement)) {
        $response["sign_up"] = true;
        $response["userName"] = $userName;
        $response["userID"] = $userID;
        $response["userPW"] = $userPW;      
    }

    echo json_encode($response);



?>