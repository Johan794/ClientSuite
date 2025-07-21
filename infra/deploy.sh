#!/bin/bash
SSH_KEY="../tf_key.pem"

INSTANCE_IP=$(terraform output -raw public_ip)


cd ./docker || exit



echo "$INSTANCE_IP"
echo "Deploying to dev env..."

scp -o StrictHostKeyChecking=no -i $SSH_KEY -r docker-compose.yml ubuntu@"$INSTANCE_IP":/home/ubuntu

ssh -o StrictHostKeyChecking=no -i $SSH_KEY ubuntu@"$INSTANCE_IP" << EOF
  sudo docker compose -f docker-compose.yml up -d
EOF

echo "Deployment complete!"