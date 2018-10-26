<?php
  require '../sql/config.php';
  header('Content-Type: application/json');
  $cActiveStatus = 1;
  // Retrieve ID from request
  if (!isset($_POST["id"])){
    $response["code"] = "-1";
    echo json_encode($response);
    exit;
  }
  $xId = $_POST["id"];
  $xDate        = DATE("Y-m-d H:i:s");
  $xDate        = substr($xDate,0,strlen($xDate)-2);
  // Get security_key for requested device
  if ($stmt = mysqli_prepare($con, "SELECT security_key FROM ACTIVE_PRODUCTS WHERE id = ?")){
    if (mysqli_stmt_bind_param($stmt, "i",$xId)){
      if (mysqli_stmt_execute($stmt)){
        if (mysqli_stmt_bind_result($stmt, $ySecurityKey)){
          if (mysqli_stmt_fetch($stmt)){
            $securityKey = $ySecurityKey;
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
  // Deactivate previous tokens
  if ($stmt = mysqli_prepare($con, "UPDATE ACTIVE_TOKENS SET status = -1 WHERE status = 1 AND fk_product_id = ?")){
    if (mysqli_stmt_bind_param($stmt,"i",$xId)){
      if (!(mysqli_stmt_execute($stmt))){
        $response["code"] = "-5";
        echo json_encode($response);
        exit;
      }
    }
  }
  mysqli_stmt_close($stmt);
  // Create salt
  $ySalt = uniqid('', true);
  // Create input for hash
  $toBeHashed = $securityKey.$ySalt;
  // Create hash using sha256
  $hash = hash("sha256",$toBeHashed);
  // Create token
  if ($stmt = mysqli_prepare($con, "INSERT INTO ACTIVE_TOKENS(fk_product_id, salt, hash, token_created, status) VALUES(?,?,?,?,?)")){
    if (mysqli_stmt_bind_param($stmt, "isssi",$xId,$ySalt,$hash,$xDate,$cActiveStatus)){
      if (mysqli_stmt_execute($stmt)){
        $response["salt"] = $ySalt;
        $response["code"] = 1;
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