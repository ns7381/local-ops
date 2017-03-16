# 参数：
# $1 git地址
# $2 Git分支
# $3 package路径

rm -rf $3
mkdir -p $3
cd $3
expect -c 'spawn git clone http://ningsheng@10.110.17.13/iop/cloud-iopm-web.git/; expect assword; send "123456a?\n"; interact'
#git checkout $2
cd $3/cloud-iopm-web
expect -c 'spawn git submodule update --init; expect sername; send "ningsheng\n"; expect assword; send "123456a?\n"; interact'
git submodule update --init
gradle war
