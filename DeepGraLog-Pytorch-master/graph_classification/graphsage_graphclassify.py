# -*- coding: utf-8 -*-
# In[1]
import time
import torch.utils.data as Data
import numpy as np
import pandas as pd
import os, sys
import argparse
import torch
import torch.nn as nn
import torch.nn.functional as F
import torch.optim as optim
import random
import math
from sklearn.utils import shuffle
from sklearn.metrics import f1_score, precision_score, recall_score
from collections import defaultdict
from tqdm import tqdm


class DataCenter_train(object):
    """
    Loading the training set
    Parameter:
        file_paths:file_path
    """
    def __init__(self, file):
        super(DataCenter_train, self).__init__()
        self.file = file

    def load_Dataset(self, dataset='node'):
        feat_list = []
        label_list = []
        node_map = {}
        label_map = {}
        adj_lists = defaultdict(set)
        count = 0  # node number

        labels = set()
        with open(self.file) as f:
            id = f.readline().replace("\n", "")
            list_edge = []
            for i in f:
                if i == 'network[son<-parent]=\n':
                    break
                else:
                    label = i.split('=')[1]
                    label = label.replace("\n", "")
                    labels.add(label)
            for line in f:
                if line == 'nodeInfo=\n':
                    break
                list_edge.append(line)

            # Get node features
            for each_sample in f.readlines():
                if each_sample == '\n':
                    break
                sample_clean = each_sample.strip().split(':')
                node_feature = sample_clean[1]
                one_feat_list = node_feature.strip().split(' ')[:]
                feat_list.append(one_feat_list)
                node_map[id + sample_clean[0]] = count
                count += 1
                print(labels)
                for item in labels:
                    if item == sample_clean[0]:
                        label_map[id + sample_clean[0]] = 0
                        break
                    else:
                        label_map[id + sample_clean[0]] = 1
                label_list.append(label_map[id + sample_clean[0]])

            # Get each node's neighbor {v0:[V0's neighbor set],v1:[V1's neighbor set]}
            for edge in list_edge:
                number = edge.strip().split(',')[1]
                each_pair = edge.strip().split(",")[0]
                pair = each_pair.strip().split("<-")
                assert len(pair) == 2
                for i in range(int(number)):
                    adj_lists[node_map[id + pair[0]]].add(node_map[id + pair[1]])

        feat_list = np.asarray(feat_list, dtype=np.float64)
        label_list = np.asarray(label_list, dtype=np.int64)


        rand_indices = np.random.permutation(feat_list.shape[0])
        train_index = rand_indices[:]
        setattr(self, dataset + '_train', train_index)
        setattr(self, dataset + '_feats', feat_list)
        setattr(self, dataset + '_labels', label_list)
        setattr(self, dataset + '_adj_lists', adj_lists)

class DataCenter_test(object):
    """
        Loading the test set
        Parameter:
            file_paths:file_path
    """
    def __init__(self, file):
        super(DataCenter_test, self).__init__()
        self.file = file

    def load_Dataset(self, dataset='test'):
        feat_list = []
        label_list = []
        node_map = {}
        label_map = {}
        adj_lists = defaultdict(set)
        count = 0 # node number

        labels = set()
        with open(self.file) as f:
            id = f.readline().replace("\n", "")
            list_edge = []
            for i in f:
                if i == 'network[son<-parent]=\n':
                    break
                else:
                    label = i.split('=')[1]
                    label = label.replace("\n", "")
                    labels.add(label)
            for line in f:
                if line == 'nodeInfo=\n':
                    break
                list_edge.append(line)

            # Get node features
            for each_sample in f.readlines():
                if each_sample == '\n':
                    break
                sample_clean = each_sample.strip().split(':')
                node_feature = sample_clean[1]
                one_feat_list = node_feature.strip().split(' ')[:]
                feat_list.append(one_feat_list)
                node_map[id + sample_clean[0]] = count
                count += 1
                print(labels)
                for item in labels:
                    if item == sample_clean[0]:
                        label_map[id + sample_clean[0]] = 0
                        break
                    else:
                        label_map[id + sample_clean[0]] = 1
                label_list.append(label_map[id + sample_clean[0]])

            # Get each node's neighbor {v0:[V0's neighbor set],v1:[V1's neighbor set]}
            for edge in list_edge:
                number = edge.strip().split(',')[1]
                each_pair = edge.strip().split(",")[0]
                pair = each_pair.strip().split("<-")
                assert len(pair) == 2
                for i in range(int(number)):
                    adj_lists[node_map[id + pair[0]]].add(node_map[id + pair[1]])


        feat_list = np.asarray(feat_list, dtype=np.float64)
        label_list = np.asarray(label_list, dtype=np.int64)

        rand_indices = np.random.permutation(feat_list.shape[0])
        test_index = rand_indices[:int(len(rand_indices)*0.6)]
        setattr(self, dataset + '_test', test_index)
        setattr(self, dataset + '_feats', feat_list)
        setattr(self, dataset + '_labels', label_list)
        setattr(self, dataset + '_adj_lists', adj_lists)

class SageLayer(nn.Module):
    """
        SageLayer
    """
    def __init__(self, input_size, out_size, gcn=False):
        super(SageLayer, self).__init__()
        self.input_size = input_size
        self.out_size = out_size
        self.gcn = gcn
        self.weight = nn.Parameter(
            torch.FloatTensor(out_size, self.input_size if self.gcn else 2 * self.input_size))
        self.init_params()

    def init_params(self):
        for param in self.parameters():
            nn.init.xavier_uniform_(param)

    def forward(self, self_feats, aggregate_feats, neighs=None):
        """
        Parameters:
            self_feats:node features
            aggregate_feats:aggregated characteristics of neighbor nodes
        """
        if not self.gcn:
            combined = torch.cat([self_feats, aggregate_feats], dim=1)
        else:
            combined = aggregate_feats
        combined = F.relu(self.weight.mm(combined.t())).t()
        return combined

class GraphSage(nn.Module):
    """GraphSage model"""
    def __init__(self, num_layers, input_size, out_size, device, gcn=False, agg_func='MEAN'):
        super(GraphSage, self).__init__()
        self.input_size = input_size
        self.out_size = out_size
        self.num_layers = num_layers
        self.gcn = gcn
        self.device = device
        self.agg_func = agg_func
        # Define the inputs and outputs for each layer
        for index in range(1, num_layers + 1):
            layer_size = out_size if index != 1 else input_size
            setattr(self, 'sage_layer' + str(index),
                    SageLayer(layer_size, out_size, gcn=self.gcn))

    def forward(self, nodes_batch, raw_features, adj_lists):
        """
            Generate an embedded representation for a batch of nodes
            Parameters:
                nodes_batch:Node of the target batch
        """
        lower_layer_nodes = list(nodes_batch)
        nodes_batch_layers = [(lower_layer_nodes,)]
        for i in range(self.num_layers):
            lower_samp_neighs, lower_layer_nodes_dict, lower_layer_nodes = self._get_unique_neighs_list(
                lower_layer_nodes, adj_lists)
            nodes_batch_layers.insert(0, (lower_layer_nodes, lower_samp_neighs, lower_layer_nodes_dict))

        assert len(nodes_batch_layers) == self.num_layers + 1

        pre_hidden_embs = raw_features
        for index in range(1, self.num_layers + 1):
            nb = nodes_batch_layers[index][0]
            pre_neighs = nodes_batch_layers[index - 1]
            aggregate_feats = self.aggregate(nb, pre_hidden_embs, pre_neighs)
            sage_layer = getattr(self, 'sage_layer' + str(index))
            if index > 1:
                nb = self._nodes_map(nb, pre_hidden_embs, pre_neighs)
            cur_hidden_embs = sage_layer(self_feats=pre_hidden_embs[nb], aggregate_feats=aggregate_feats)
            pre_hidden_embs = cur_hidden_embs
        return pre_hidden_embs

    def _nodes_map(self, nodes, hidden_embs, neighs):
        layer_nodes, samp_neighs, layer_nodes_dict = neighs
        assert len(samp_neighs) == len(nodes)
        index = [layer_nodes_dict[x] for x in nodes]
        return index

    def _get_unique_neighs_list(self, nodes, adj_lists, num_sample=1):
        _set = set
        # Get all the neighbor nodes of the target node set,[[V0's neighbor],[V1's neighbor],...]
        to_neighs = [adj_lists[int(node)] for node in nodes]
        if not num_sample is None:
            _sample = random.sample
            # [set(randomly sampled set of neighbors),set(),set()]
            samp_neighs = [_set(_sample(to_neigh, num_sample)) if len(to_neigh) >= num_sample else to_neigh for to_neigh in to_neighs]
        else:
            samp_neighs = to_neighs
        samp_neighs = [samp_neigh | set([nodes[i]]) for i, samp_neigh in enumerate(samp_neighs)]
        _unique_nodes_list = list(set.union(*samp_neighs))
        i = list(range(len(_unique_nodes_list)))
        unique_nodes = dict(list(zip(_unique_nodes_list, i)))
        return samp_neighs, unique_nodes, _unique_nodes_list

    def aggregate(self, nodes, pre_hidden_embs, pre_neighs, num_sample=10):
        """
            Example Aggregate neighbor node information
            Parameters:
                nodes:node set
                pre_hidden_embs:node embeddings of the previous layer
                pre_neighs:nodes of the previous layer
        """
        unique_nodes_list, samp_neighs, unique_nodes = pre_neighs
        assert len(nodes) == len(samp_neighs)
        indicator = [(nodes[i] in samp_neighs[i]) for i in range(len(samp_neighs))]
        assert (False not in indicator)
        if not self.gcn:
            samp_neighs = [(samp_neighs[i] - set([nodes[i]])) for i in range(len(samp_neighs))]
        if len(pre_hidden_embs) == len(unique_nodes):
            embed_matrix = pre_hidden_embs
        else:
            embed_matrix = pre_hidden_embs[torch.LongTensor(unique_nodes_list)]

        # Building an adjacency matrix involving nodes
        mask = torch.zeros(len(samp_neighs), len(unique_nodes))
        column_indices = [unique_nodes[n] for samp_neigh in samp_neighs for n in samp_neigh]
        row_indices = [i for i in range(len(samp_neighs)) for j in range(len(samp_neighs[i]))]
        mask[row_indices, column_indices] = 1

        if self.agg_func == 'MEAN':
            num_neigh = mask.sum(1, keepdim=True)
            mask = mask.div(num_neigh).to(embed_matrix.device)
            # Example Aggregate information about neighboring nodes
            aggregate_feats = mask.mm(embed_matrix)

        elif self.agg_func == 'MAX':
            indexs = [x.nonzero() for x in mask == 1]
            aggregate_feats = []
            for feat in [embed_matrix[x.squeeze()] for x in indexs]:
                if len(feat.size()) == 1:
                    aggregate_feats.append(feat.view(1, -1))
                else:
                    aggregate_feats.append(torch.max(feat, 0)[0].view(1, -1))
            aggregate_feats = torch.cat(aggregate_feats, 0)
        return aggregate_feats

class Classification(nn.Module):
    """
        A one-tier classification model
        Parameters:
            input_size:input size
            num_classes:number of class
        return:
            logists:The maximum probability corresponds to the label
    """

    def __init__(self, input_size, num_classes):
        super(Classification, self).__init__()
        self.fc1 = nn.Linear(input_size, num_classes)
        self.init_params()

    def init_params(self):
        for param in self.parameters():
            if len(param.size()) == 2:
                nn.init.xavier_uniform_(param)

    def forward(self, x):
        logists = torch.log_softmax(self.fc1(x), 1)
        return logists

def evaluate(dataCenter, ds, graphSage, classification, device, name, cur_epoch):
    """
        Test the performance of the model
        Parameters:
            datacenter:Created Datacenter object
            ds:The name of the dataset
            graphSage：The trained graphSage object
            classification:The trained classificator
    """
    test_nodes = getattr(dataCenter, ds + '_test')
    labels = getattr(dataCenter, ds + '_labels')
    feature_data = torch.FloatTensor(getattr(dataCenter, ds + '_feats'))
    adj_lists = getattr(dataCenter, ds + '_adj_lists')

    models = [graphSage, classification]
    params = []
    for model in models:
        for param in model.parameters():
            if param.requires_grad:
                param.requires_grad = False
                params.append(param)

    embs = graphSage(test_nodes, feature_data, adj_lists)
    logists = classification(embs)
    _, predicts_test = torch.max(logists, 1)
    labels_test = labels[test_nodes]
    assert len(labels_test) == len(predicts_test)
    comps = zip(labels_test, predicts_test.data)
    labels_test = list(labels_test)
    predicts_test = predicts_test.data.numpy().tolist()

    for param in params:
        param.requires_grad = True

    return labels_test,predicts_test

def apply_model(list_file, graphSage, classification, b_sz, device):
    """
        Training
        Parameters:
            list_file: file address in a batch
            graphSage:Graphsage object that need to be trained
            classification:Classificator that need to be trained
            b_sz:batch_size
        return:
            graphSage：The trained GraphSage object
            classification:The trained Classificator
            loss:The batch of loss
    """
    models = [graphSage, classification]
    params = []
    for model in models:
        for param in model.parameters():
            if param.requires_grad:
                params.append(param)

    optimizer = torch.optim.SGD(params, lr=0.1)
    optimizer.zero_grad()
    for model in models:
        model.zero_grad()

    loss = 0

    for file in list_file:
        dc = DataCenter_train(file)
        dc.load_Dataset()
        ds = 'node'
        train_nodes = getattr(dc, ds + '_train')
        labels = getattr(dc, ds + '_labels')
        feature_data = torch.FloatTensor(getattr(dc, ds + '_feats'))
        adj_lists = getattr(dc, ds + '_adj_lists')

        train_nodes = shuffle(train_nodes)

        batches = math.ceil(len(train_nodes) / b_sz)

        visited_nodes = set()

        for index in range(batches):
            nodes_batch = train_nodes[index * b_sz:(index + 1) * b_sz]
            visited_nodes |= set(nodes_batch)

            # get ground-truth for the nodes batch
            labels_batch = labels[nodes_batch]

            # feed nodes batch to the graphSAGE
            embs_batch = graphSage(nodes_batch, feature_data, adj_lists)

            # superivsed learning
            logists = classification(embs_batch)
            loss_sup = -torch.sum(logists[range(logists.size(0)), labels_batch], 0)
            loss_sup /= len(nodes_batch)
            loss += loss_sup

    loss.backward()
    # Update parameter
    for model in models:
        nn.utils.clip_grad_norm_(model.parameters(), 5)
    optimizer.step()

    optimizer.zero_grad()
    for model in models:
        model.zero_grad()
    return graphSage, classification, loss

def get_file_path(root_path, file_list, dir_list):
    """
        Gets all files under this file address
    """
    dir_or_files = os.listdir(root_path)
    for dir_file in dir_or_files:
        dir_file_path = os.path.join(root_path, dir_file)
        if os.path.isdir(dir_file_path):
            dir_list.append(dir_file_path)
            get_file_path(dir_file_path, file_list, dir_list)
        else:
            file_list.append(dir_file_path)