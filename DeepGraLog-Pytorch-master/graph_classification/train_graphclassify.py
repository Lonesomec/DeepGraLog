from graphsage_graphclassify import *

dimensionality = 12 # node feature dimension
path_file=' '# Dataset address
p_input = input('1. training3 test7,  2. training5 test5\n') # Training set and test set partition proportion
if p_input == '1':
    divide = 0.3
elif p_input == '2':
    divide = 0.5
else:
    print('Input error!')

#Load Dataset
list_file = []
dir_file = []

list_train=[] # the training set file address
list_test=[]  # the test set file address

get_file_path(path_file, list_file, dir_file)
list_file=shuffle(list_file)

for i in list_file[:int(len(list_file) * divide)]:
    list_train.append(i)
for j in list_file[int(len(list_file) * divide):]:
    list_test.append(j)

batch_size = 32
random.seed(824)
np.random.seed(824)
torch.manual_seed(824)
torch.cuda.manual_seed_all(824)

epochs = 10

device = torch.device("cuda" if torch.cuda.is_available() else "cpu")
graphSage = GraphSage(1, dimensionality, 16, device, gcn='store_true', agg_func='MEAN')  # Create the GraphSage object

num_labels = 2

classification = Classification(16, num_labels) # Create the classificator
print('GraphSage with Supervised Learning')

#Training
for epoch in tqdm(range(epochs)):
    time.sleep(0.0001)
    print('----------------------EPOCH %d-----------------------' % epoch)
    i = 0
    loss_all = 0
    list_train = shuffle(list_train)
    for count in range(math.ceil(len(list_train) / batch_size)):
        if len(list_train[i:i + batch_size]) == batch_size:
            graphSage, classification, loss = apply_model(list_train[i:i + batch_size], graphSage, classification,1, device)
            loss_all += loss
        else:
            graphSage, classification, loss = apply_model(list_train[i:], graphSage, classification, 1, device)
            loss_all += loss
        i += batch_size
    print('loss:', loss_all/len(list_train))

# Verify model performance on the test set
labels_test_all=[]
predicts_test_all=[]
num = 0
list_test=shuffle(list_test)
for count in tqdm(range(math.ceil(len(list_test) / batch_size))):
    time.sleep(0.0001)
    max_vali_f1 = 0
    if len(list_test[num:num + batch_size]) == batch_size:
        for file in list_test[num:num + batch_size]:
            dc_test = DataCenter_test(file)
            dc_test.load_Dataset()
            ds_test = 'test'
            labels_test, predicts_test = evaluate(dc_test, ds_test, graphSage, classification, device, 'debug', epoch)
            # Generated graph label
            if 0 not in list(labels_test):
                graph_lable = 1
            else:
                graph_lable = 0

            #Generate graph prediction results
            if 0 not in list(predicts_test):
                predicts = 1
            else:
                predicts = 0

            labels_test_all.append(graph_lable)
            predicts_test_all.append(predicts)
    else:
        for file in list_test[num:]:
            dc_test = DataCenter_test(file)
            dc_test.load_Dataset()
            ds_test = 'test'
            labels_test, predicts_test = evaluate(dc_test, ds_test, graphSage, classification, device, 'debug', epoch)
            # Generated graph label
            if 0 not in list(labels_test):
                graph_lable = 1
            else:
                graph_lable = 0

            # Generate graph prediction results
            if 0 not in list(predicts_test):
                predicts = 1
            else:
                predicts = 0

            labels_test_all.append(graph_lable)
            predicts_test_all.append(predicts)
    num += batch_size

#Calculate F1,Precision,recall
labels = np.array(labels_test_all)
scores = np.array(predicts_test_all)
TP = 0
FP = 0
FN = 0
TN = 0
for k in range(0, labels.shape[0]):
    if scores[k] ==1 and labels[k] == 1:
        TP += 1
    if scores[k] == 1 and labels[k] ==0:
        FP += 1
    if scores[k] == 0 and labels[k] == 1:
        FN += 1
    if scores[k] == 0 and labels[k] == 0:
        TN += 1
accuracy = (TP + TN) / (TP + FP + FN + TN)
precision = TP / (TP + FP)
recall = TP / (TP + FN)
F1 = 2 * precision * recall / (precision + recall)

print('test_f1_all',F1)
print('test_p_all',precision)
print('test_r_all', recall)

