(ns scratch.ssh.play
  "Discover clj-ssh"
  (:require [clj-ssh.cli :as cli]
            [clj-ssh.ssh :as ssh]))

(comment
  (cli/ssh "some-host" :username "some-user" "ls"))
;;; {:exit 0, :out "check-network-sane.sh\ncluster-conf\netc\netc-bak\ninstall\nprovisioning-1.3.tar.gz\nopt\ntools\nvar\nwhirr.log\n", :err ""}
