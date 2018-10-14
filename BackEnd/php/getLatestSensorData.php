<?php
  require '../sql/config.php';
  header('Content-Type: application/json');
  // No ID provided
  if (!isset($_GET["id"])){
    $response["action"] = -1;
    echo json_encode($response);
    exit;
  }

  // Retrieve id from request
  $xId = $_GET["id"];

  if ($stmt = mysqli_prepare($con, "SELECT temperature, humidity FROM COLLECTED_DATA WHERE id = (SELECT MAX(id) FROM COLLECTED_DATA WHERE fk_product_id = ?)")){
    if (mysqli_stmt_bind_param($stmt,"i",$xId)){
      if (mysqli_stmt_execute($stmt)){
        if (mysqli_stmt_bind_result($stmt,$yTemperature,$yHumidity)){
          if (mysqli_stmt_fetch($stmt)){
            $response["action"] = 1;
            $response["temp"]   = $yTemperature;
            $response["hum"]    = $yHumidity;
            mysqli_stmt_close($stmt);
          } else{
            $response["action"] = 0;
          }
        } else{
          $response["action"] = -5;
        }
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