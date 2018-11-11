<?php
  require '../sql/config.php';
  header('Content-Type: application/json');
  // Load data from request
  $xId    = $_GET["id"];
  $xStart = $_GET["start"];
  $xEnd   = $_GET["end"];
  // When ID is empty
  if (!isset($xId)){
      $response["code"] = -1;
      echo json_encode($response);
      exit;
  }
  // When start & end are not provided
  if (empty($xStart) && empty($xEnd)){
      $response["code"] = -3;
      echo json_encode($response);
      exit;
  }
  // Fill missing param
  if (empty($xStart)){
    $xStart = "1000-01-01";
  }
  else if (empty($xEnd)) {
    $xEnd = "3000-01-01";
  }
  // Execute query and create JSON with data
  if ($stmt = mysqli_prepare($con, "SELECT rep_date,temperature,humidity FROM COLLECTED_DATA WHERE fk_product_id = ? AND rep_date BETWEEN ? AND ?")){
      if (mysqli_stmt_bind_param($stmt,"iss",$xId,$xStart,$xEnd)){
          if (mysqli_stmt_execute($stmt)){
              if (mysqli_stmt_bind_result($stmt,$yDate,$yTemperature,$yHumidity)){
                  $data = array();
                  while (mysqli_stmt_fetch($stmt)){
                    $rep = array();
                    $rep["date"] = $yDate;
                    $rep["temp"] = $yTemperature;
                    $rep["hum"]  = $yHumidity;
                    $data[] = $rep;
                  }
                  $response["amount"] = count($data);
                  $response["data"] = $data;
              }else{
                $response["code"] = -13;
              }
          }else{
            $response["code"] = -12;
          }
      }else{
        $response["code"] = -11;
      }
  } else{
    $response["code"] = -10;
  }
  echo json_encode($response);
?>