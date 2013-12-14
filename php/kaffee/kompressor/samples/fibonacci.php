The fibonacci sequence is: <br/>1<br/>2<br/>

<?php
 // The first two numbers are already printed
$x = 1;
$y = 2;
/*
 The rest is generated below
*/
while ($z < 1000) {

    $z = $x + $y;
    echo($z . "<br/>");
    $x = $y;
    $y = $z;
}
?>
