#!/bin/zsh

image_name="im-user"

container_name="im-user-c"

old_container=$(sudo docker ps -a | grep $container_name | awk '{print $1}')

if [ ! -n "$old_container" ]; then
  echo "没有相关容器，跳过容器停止步骤"
else
  sudo docker stop $container_name
  if [ $? -ne 0 ]; then
    echo "关闭镜像失败"
    exit 1
  else
    echo echo "------------------原容器已关闭--------------------"
  fi

  sudo docker rm $old_container
  echo "------------------原容器已删除--------------------"
fi


old_images=$(sudo docker images | grep $image_name | awk '{print $1}')

if [ ! -n "$old_images" ]; then
  echo "没有相关镜像，跳过镜像删除步骤"
else
  sudo docker rmi $old_images
  if [ $? -ne 0 ]; then
    echo "删除镜像失败"
    exit 1
  else
    echo echo "------------------原镜像已删除--------------------"
  fi
fi


sudo docker build -t $image_name .

if [ $? -ne 0 ]; then
  echo "镜像构建失败"
  exit 1
else
  echo "------------------新镜像已构建--------------------"
fi


sudo docker run --name $container_name -p 8001:8001 -p 20001:20001 -d $image_name

if [ $? -ne 0 ]; then
  echo "容器启动失败"
  exit 1
else
  echo "------------------容器已启动--------------------"
fi
