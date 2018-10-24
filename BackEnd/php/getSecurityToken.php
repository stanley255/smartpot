<?php
  require '../sql/config.php';
  header('Content-Type: application/json');
  $cActiveStatus = 1;
  // Ziskanie IDcka z requestu
  if (!isset($_GET["id"])){
    exit;
  }

  $xId = $_GET["id"];
  $xDate        = DATE("Y-m-d h:i:sa");
  $xDate        = substr($xDate,0,strlen($xDate)-2);
  
  // Vytvorenie saltu
  $ySalt = uniqid('', true);

  // Vytvorenie tokenu
  if ($stmt = mysqli_prepare($con, "INSERT INTO ACTIVE_TOKENS(fk_product_id, salt, token_created, status) VALUES(?,?,?,?)")){
    if (mysqli_stmt_bind_param($stmt, "issi",$xId,$ySalt,$xDate,$cActiveStatus)){
      if (mysqli_stmt_execute($stmt)){
        $response["salt"] = $ySalt;
        $response["action"] = 1;
      } else{
        // Nepodari sa zapisat za predpokladu, ze id je nespravne
        $response["action"] = -3;
      }
    } else{
      $response["action"] = -2;
    }
  } else{
    $response["action"] = -1;
  }
  echo json_encode($response);
?>