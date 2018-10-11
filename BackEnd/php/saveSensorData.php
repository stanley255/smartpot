<?php
  require '../sql/config.php';
  header('Content-Type: application/json');
  // Retrieve collected data from request
  $xProductId   = $_GET["id"];
  $xTemperature = $_GET["temp"];
  $xHumidity    = $_GET["hum"];
  // Check data
  if (is_null($xProductId) or is_null($xTemperature) or is_null($xHumidity)){
    $response["action"] = -1;
    echo json_encode($response);
    exit;
  }
  // Correct data if necessary
  $xTemperature = str_replace(',','.',$xTemperature);
  $xHumidity    = str_replace(',','.',$xHumidity);
  // Insert values into DataBase
  if ($stmt = mysqli_prepare($con, "INSERT INTO COLLECTED_DATA(fk_product_id, temperature, humidity) VALUES(?,?,?)")){
    if (mysqli_stmt_bind_param($stmt,"idd",$xProductId,$xTemperature,$xHumidity)){
      if (mysqli_stmt_execute($stmt)){
        mysqli_stmt_close($stmt);
        $response["action"] = 1;
      } else{
        $response["action"] = -4;
      }
    } else{
      $response["action"] = -3;
    }
  } else{
    $response["action"] = -2;
  }
  echo json_encode($response);
?>
