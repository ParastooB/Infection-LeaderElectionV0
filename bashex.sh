# too add to .cshrc
# echo 'Going to bashrc'
# exec bash
# .bashrc

PS1='\[\e[1;35m\][\u@\h \w]\$\[\e[0m\] '

# Source global definitions
if [ -f /etc/bashrc ]; then
	. /etc/bashrc
fi

while true
do

value=$(</home/user/test.txt)
echo "$value"

setterm -term linux -back "$value" -fore white -clear

sleep 5

done