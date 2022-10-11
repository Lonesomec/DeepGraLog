"""
    创建邻接矩阵
"""
import numpy as np
from scipy import sparse

class Adj:
    def __init__(self,edge_path,features):
        self.edge_path=edge_path
        self.features=features
        self.set_node=set()
        self.list_edge = []

    #保存边的信息
    def delete_node(self):
        f = open(self.edge_path, 'r+')
        f.readline()
        self.dict_edge={}
        for line in f.readlines():
            line = line.strip('\n')
            edge01 = line.split(':')[0].split('<-')[0]
            self.set_node.add(edge01)
            edge02 = line.split(':')[0].split('<-')[1]
            self.set_node.add(edge02)
            self.list_edge.append((edge02,edge01))
            number = line.split(':')[1]  # 保存边的次数
            self.dict_edge[(edge02,edge01)]=float(number)
        self.adj = np.zeros((self.features.shape[0],self.features.shape[0]), float)#创建初始邻接矩阵


    #创建邻接矩阵
    def get_adj(self):
        list_node=list(self.set_node)
        for i in range(len(list_node)):
            for j in range(len(list_node)):
                edge=(list_node[i], list_node[j])
                if edge in self.list_edge:
                    self.adj[i][j]=self.dict_edge[edge]
        return self.adj

    def api(self):
        self.delete_node()
        self.get_adj()
        return self.adj

if __name__ == '__main__':
    edge_path = r'E:\Graph_Network\pygcn-master\global_graph\edge'
    adj=Adj(edge_path,features)
    A=adj.delete_node()
    a=adj.get_adj()
    print(a)
    X_csr = sparse.csr_matrix(a)
    print(X_csr.shape)



