PS1='\[\e[1;35m\][\u@\h \w]\$\[\e[0m\] '

# Source global definitions
if [ -f /etc/bashrc ]; then
	. /etc/bashrc
fi
echo -ne "\e[8;55;220t"


# cat /etc/machine-id
machine=$(</etc/machine-id)
echo '------------------------------------------'
echo "$machine"

agent=`~paras273/dome.sh`

while true
do

  value=`cat ~paras273/paras273/Infection-LeaderElectionV0/agents/a_$agent.txt`
  setterm -term linux -back "$value" -fore white -clear
  sleep 0.1
done
