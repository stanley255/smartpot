<?php
  require '../sql/config.php';
  $xProductId   = $_GET["id"];
  $xTemperature = str_replace(',','.',$_GET["temp"]);
  $response["id"] = $xProductId;
  $response["temp"] = $xTemperature;
  if ($stmt = mysqli_prepare($con, "INSERT INTO COLLECTED_DATA(fk_product_id, temperature) VALUES(?,?)")){
    if (mysqli_stmt_bind_param($stmt,"id",$xProductId,$xTemperature)){
      if (mysqli_stmt_execute($stmt)){
        mysqli_stmt_close($stmt);
        $response["action"] = 1;
        echo json_encode($response);
      } else{
        $response["action"] = -3;
        echo json_encode($response);
      }
    } else{
      $response["action"] = -2;
      echo json_encode($response);
    }
  } else{
    $response["action"] = -1;
    echo json_encode($response);
  }
?>
