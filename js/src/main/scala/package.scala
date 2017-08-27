package edu.holycross.shot


import scala.scalajs.js
// Useless? import js.JSStringOps._

/** Package for representing text in Ancient Greek, since fundamental
* assumption of Unicode that script code points belong to a language is wrong.
*/
package object greek {


  def literaryAsciiOf (s: String): String = {
    if (s.head.toInt > 127) {
      // Probably ONLY WORKS FOR Unicode in form NFC
      LiteraryGreekString.nfcToAscii(s,"")

    } else {
      s
    }
  }

  def literaryUcodeOf(s: String) : String = {
    if (s.head.toInt > 127) {
      s
    } else {
      LiteraryGreekString.asciiToUcode(s,"")
    }
  }





  //////////////////////////////////////////
  def atticAsciiOf(s: String) : String = {
    "ATTIC ASCII OF  " + s
  }
  def atticUcodeOf(s: String) : String = {
    "ATTIC Ucode OF  " + s
  }
}
