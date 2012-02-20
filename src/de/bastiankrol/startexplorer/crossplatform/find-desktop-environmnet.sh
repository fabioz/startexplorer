#!/bin/bash
if [ "$(pidof gnome-session)" ]; then
   # echo "GNOME running."
   exit 11
elif [ "$(pidof ksmserver)" ]; then
   # echo "KDE running."
   exit 12
elif [ "$(pidof xfce4-session)" -o "$(pidof xfce-mcs-manage)" ]; then
   # echo "Xfce running."
   exit 13
elif [ "$(pidof lxsession)" ]; then
   # echo "LXDE running."
   exit 14
fi
exit 99
