<?php
  require '../sql/config.php';
  header('Content-Type: application/json');
  // Retrieve collected data from request
  $xId          = $_POST["id"];
  $xTemperature = $_POST["temp"];
  $xHumidity    = $_POST["hum"];
  $xKey         = $_POST["key"];
  // Check data
  if (empty($xId) or empty($xTemperature) or empty($xHumidity) or empty($xKey)){
    $response["code"] = -1;
    echo json_encode($response);
    exit;
  }
  // Check key from request
  if ($stmt = mysqli_prepare($con, "SELECT hash FROM ACTIVE_TOKENS WHERE fk_product_id = ? AND status = 1")){
    if (mysqli_stmt_bind_param($stmt,"i",$xId)){
      if (mysqli_stmt_execute($stmt)){
        if (mysqli_stmt_bind_result($stmt, $yHash)){
          if (mysqli_stmt_fetch($stmt)){
            $Hash = $yHash;
          } else{
            $response["code"] = "-3";
            echo json_encode($response);
            exit;
          }
        }
      }
    }
  }
  mysqli_stmt_close($stmt);
  if ($Hash != hash("sha256",$xKey)/*$xKey*/){
    $response["code"] = "-5";
    echo json_encode($response);
    exit;
  }
  // Create report date
  $xDate        = DATE("Y-m-d H:i:sa");
  $xDate        = substr($xDate,0,strlen($xDate)-2);
  // Correct data if necessary
  $xTemperature = str_replace(',','.',$xTemperature);
  $xHumidity    = str_replace(',','.',$xHumidity);
  // Insert values into DataBase
  if ($stmt = mysqli_prepare($con, "INSERT INTO COLLECTED_DATA(fk_product_id, temperature, humidity, rep_date) VALUES(?,?,?,?)")){
    if (mysqli_stmt_bind_param($stmt,"idds",$xId,$xTemperature,$xHumidity, $xDate)){
      if (mysqli_stmt_execute($stmt)){
        $response["code"] = 2;
      } else{
        $response["code"] = -6;
      }
    } else{
      $response["code"] = -4;
    }
  } else{
    $response["code"] = -2;
  }
  mysqli_stmt_close($stmt);
  // Change token status
  $tokenStatus = $response["code"] == 2 ? 0 : -2;
  if ($stmt = mysqli_prepare($con, "UPDATE ACTIVE_TOKENS SET status = ? WHERE status = 1 AND fk_product_id = ?")){
    if (mysqli_stmt_bind_param($stmt,"ii",$tokenStatus,$xId)){
      if (mysqli_stmt_execute($stmt)){
        $response["code"] = 1;
      }
    }
  }
  mysqli_stmt_close($stmt);
  echo json_encode($response);
?>
