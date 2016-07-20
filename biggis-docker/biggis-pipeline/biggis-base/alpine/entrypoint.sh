#!/bin/sh

# Add local user
# Either use the USER_ID if passed in at runtime or
# fallback

USER_ID=${USER_ID:-9001}
USER=${USER_NAME:-biggis}

echo "Starting with UID : $USER_ID"
addgroup -S -g $USER_ID $USER
adduser -h /home/$USER -u $USER_ID -s /bin/sh -G $USER -S $USER
chown -R $USER:$USER /home/$USER /opt /tmp /storage
export HOME=/home/$USER

exec gosu $USER "$@"
