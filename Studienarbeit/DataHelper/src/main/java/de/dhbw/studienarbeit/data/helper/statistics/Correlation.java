package de.dhbw.studienarbeit.data.helper.statistics;

import java.util.List;

public class Correlation
{
	private Correlation()
	{}

	public static double of(List<? extends Correlatable> list)
	{
		double[] x = new double[list.size()];
		double[] y = new double[list.size()];

		for (int i = 0; i < list.size(); i++)
		{
			x[i] = list.get(i).getX();
			y[i] = list.get(i).getY();
		}

		return Correlation.of(x, y);
	}

	public static double of(double[] xs, double[] ys)
	{
		if (xs.length != ys.length)
		{
			return 0.0;
		}
		if (xs.length == 0)
		{
			return 0.0;
		}

		double sx = 0.0;
		double sy = 0.0;
		double sxx = 0.0;
		double syy = 0.0;
		double sxy = 0.0;

		int n = xs.length;

		for (int i = 0; i < n; ++i)
		{
			double x = xs[i];
			double y = ys[i];

			sx += x;
			sy += y;
			sxx += x * x;
			syy += y * y;
			sxy += x * y;
		}

		// covariation
		double cov = sxy / n - sx * sy / n / n;
		// standard error of x
		double sigmax = Math.sqrt(sxx / n - sx * sx / n / n);
		// standard error of y
		double sigmay = Math.sqrt(syy / n - sy * sy / n / n);

		// div/0
		if (sigmax == 0.0 || sigmay == 0.0)
		{
			return 0.0;
		}

		// correlation is just a normalized covariation
		return cov / sigmax / sigmay;
	}
}
