<?php
  $xSSID = $_POST["ssid"];
  $xPassword = $_POST["pass"];

  $response["code"] = empty($xSSID) || empty($xPassword) ? -1 : 1;
  echo json_encode($response);
?>