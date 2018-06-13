# to add to .cshrc
# echo 'Going to bashrc'
# exec bash

# Copy this into .bashrc

PS1='\[\e[1;35m\][\u@\h \w]\$\[\e[0m\] '

# Source global definitions
if [ -f /etc/bashrc ]; then
	. /etc/bashrc
fi

# cat /etc/machine-id
machine=$(</etc/machine-id)
echo '------------------------------------------'
echo "$machine"

case $machine in
7f4b579defc14217b47dfda8812ae437)
	echo "You are agent 4"
	agent="4"
	;;
692ff96d900f48fa885e7feaca9fd80b)
	echo "You are agent 3"
	agent="3"
	;;
ce1ba65c19014940a6fe6b9e4779a587)
	echo "You are agent 2"
	agent="2"
	;;
f2f676244497454f97e78b38010c4acd)
	echo "You are agent 1"
	agent="1"
	execute=true
	;;
*)
	echo "Sorry, I don't understand"
	;;
esac

while true
do

value=$(</eecs/home/paras273/paras273/Infection-LeaderElectionV0/agents/a_$agent.txt)

setterm -term linux -back "$value" -fore white -clear
echo "$value"

echo "$agent"
echo "$execute"
if [ "$agent" = "1" -a "$execute" = true ] 
then
	setterm -term linux -back red -fore white -clear
	execute=false
	exec /cs/home/paras273/paras273/Infection-LeaderElectionV0/run.sh &
fi

sleep 1

done
