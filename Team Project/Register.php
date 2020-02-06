<?php 
    $con = mysqli_connect("localhost", "yjslh", "yj135246!", "yjlsh");
    mysqli_query($con,'SET NAMES utf8');

    $userName = $_POST["userName"];
    $userID = $_POST["userID"];
    $userPW = $_POST["userPW"];

    $statement = mysqli_prepare($con, "INSERT INTO USER VALUES (?,?,?)");
    mysqli_stmt_bind_param($statement, "sssi", $userName, $userID, $userPW);
    mysqli_stmt_execute($statement);


    $response = array();
    $response["sign_up"] = true;
 
   
    echo json_encode($response);



?>