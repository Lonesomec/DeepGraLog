### CONFIGS ###
dataset = 'cora'
model = 'GAE'

# input_dim = 1433
input_dim = 33  #输入的特征纬度  one-hot=54 ,类图输出特征8加上gephi的维度11（19） ，全局图gephi11, 张的数据全局图维度33（one-hot）
hidden1_dim = 32
hidden2_dim = 8 #输出的特征纬度
use_feature = True

num_epoch = 500
learning_rate = 0.01