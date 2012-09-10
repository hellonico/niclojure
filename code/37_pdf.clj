(import '[com.lowagie.text.pdf PdfEncryptor PdfReader PdfWriter])

(def perms (bit-xor PdfWriter/AllowAssembly PdfWriter/AllowCopy PdfWriter/AllowFillIn PdfWriter/AllowModifyContents PdfWriter/AllowPrinting PdfWriter/AllowScreenReaders))

(defn decrypt[filename]
	(PdfEncryptor/encrypt (PdfReader. filename) (java.io.FileOutputStream. "unlocked.pdf") perms nil nil false))