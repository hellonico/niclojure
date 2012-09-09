(use 'postal.core)

(send-message ^{	:host "smtp.gmail.com"
                    :user "<username>"
                    :pass "<password>"
                    :ssl :yes!!!11
                }
                {	:from "hellonico@gmail.com"
                    :to "hellonico@gmail.com"
                    :subject "Hi!"
                    :body [ 
                    	{:content "<html>新橋</html>" :type "text/html; charset=utf-8"}
                        {:type :attachment :content (java.io.File. "code/mail.txt")}
                        {	:type :inline 
                        	:content (java.io.File. "code/mail.txt") 
                        	:content-type "application/text"}
					]})