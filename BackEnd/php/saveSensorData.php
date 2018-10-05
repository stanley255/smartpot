<?php
  require '../sql/config.php';
  $xProductId   = $_POST["id"];
  $xTemperature = $_POST["temp"];

  if ($stmt = mysqli_prepare($con, "INSERT INTO SMARTPOT.COLLECTED_DATA(fk_product_id, temperature) VALUES(?,?)")){
    if (mysqli_stmt_bind_param($stmt,"ii",$xProductId,$xTemperature)){
      if (mysqli_stmt_execute($stmt)){
        $response["action"] = 1;
        echo json_encode($response);
      } else{
        $response["action"] = -3;
      }
    } else{
      $response["action"] = -2;
    }
  } else{
    $response["action"] = -1;
  }
  mysqli_stmt_close($stmt);
  echo $response;
?>
