"""
    输入数据预处理阶段
"""

import numpy as np
from scipy import sparse
from adj import *


def load_Dataset(node_path,edge_path):
    """读取存放在指定路径的数据集"""
    feat_list = []  # 用于存放每个节点特征向量的列表

    #获取节点特征
    node_path = node_path  # 获取数据集的地址
    with open(node_path) as f1:
        f1.readline()
        f1.readline()
        for i, each_sample in enumerate(f1.readlines()):  # 遍历每个样本的特征
            sample_clean = each_sample.strip().split(':')
            node_feature = sample_clean[1]
            one_feat_list = []#保存每行特征
            for item in node_feature.strip().split(' '):
                one_feat_list.append(float(item))
            feat_list.append(one_feat_list)
    feat_list = np.array(feat_list)
    features = sparse.lil_matrix(feat_list)

    #获取邻接矩阵
    a_obj= Adj(edge_path, features)
    a_obj.delete_node()
    adj = a_obj.get_adj()
    adj = sparse.csr_matrix(adj)

    return features,adj




if __name__ == '__main__':
    edge_path = r'D:\360MoveData\Users\Asus\Desktop\vgae_pytorch-master\global graph\edge'
    node_path=r'D:\360MoveData\Users\Asus\Desktop\vgae_pytorch-master\global graph\node'
    features,adj=load_Dataset(node_path, edge_path)

    print(features)
    print('===========================')
    print(adj)