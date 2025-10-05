import pandas as pd
import matplotlib.pyplot as plt
df = pd.read_csv("results.csv")
for dist in df['distribution'].unique():
    g = df[df['distribution']==dist].groupby('n')['timeMs'].mean()
    plt.plot(g.index, g.values, label=dist)
plt.xscale('log')
plt.yscale('log')
plt.xlabel('n')
plt.ylabel('timeMs (avg)')
plt.legend()
plt.savefig('docs/performance-plots/time_vs_n.png')
plt.show()
