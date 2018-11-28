<?php
  require '../sql/config.php';
  header('Content-Type: application/json');
  // Retrieve id from request
  $xId = $_GET["id"];
  // No ID provided
  if (empty($xId)){
    $response["code"] = -1;
    echo json_encode($response);
    exit;
  }

  if ($stmt = mysqli_prepare($con, "SELECT temperature, humidity FROM COLLECTED_DATA WHERE id = (SELECT MAX(id) FROM COLLECTED_DATA WHERE fk_product_id = ?)")){
    if (mysqli_stmt_bind_param($stmt,"i",$xId)){
      if (mysqli_stmt_execute($stmt)){
        if (mysqli_stmt_bind_result($stmt,$yTemperature,$yHumidity)){
          if (mysqli_stmt_fetch($stmt)){
            $response["code"] = 1;
            $response["temp"] = $yTemperature;            
            $response["hum"]  = $yHumidity;
            mysqli_stmt_close($stmt);
          } else{
            $response["code"] = -3;
          }
        } else{
          $response["code"] = -8;
        }
      } else{
        $response["code"] = -6;
      }
    } else{
      $response["code"] = -4;
    }
  } else{
    $response["code"] = -2;
  }
  echo json_encode($response);
?>