The fibonacci sequence is: <br/>1<br/>2<br/>

<?php

$x=1;
$y=2;



while($z<1000){

$z=$x+$y;
echo($z."<br/>");
$x=$y;
$y=$z;
}
?>
