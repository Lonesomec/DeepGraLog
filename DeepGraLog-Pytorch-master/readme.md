# Model

This is the Pytorch implementation of GraphSage for cross-graph node classification task and graph classification task in this experiment, as described in our paper:

论文地址

## Requirements
```bash
python requirements.py install
```

## Tasks
Our study supports GraphSAGE for two different tasks:Graph classification and Node classification across graphs.
- You can run `train_graphclassify.py` to realize the task of graph classification to carry out traditional log anomaly detection. 
- You can also run `train_nodeclassify.py` to achieve code-file level exception detection through node classification across graphs.
- What's more, we provide an additional version `train_errortype.py` to probe into the transfer ability of DeepGraLog for four fault types.